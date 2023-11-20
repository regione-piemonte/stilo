/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.impl.filesystem.FileSystemStorageConfig;
import it.eng.utility.storageutil.manager.HibernateUtil;
import it.eng.utility.storageutil.manager.entity.Storages;
import it.eng.utility.storageutil.util.XMLUtil;
import it.eng.utility.ui.servlet.bean.TreeStorageBean;
import it.eng.utility.ui.servlet.bean.TreeStorageConfigBean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/AdminStorage")
public class AdminStorage {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getInfoStorage(ModelMap model, HttpSession httpSession, HttpServletRequest servletrequest, HttpServletResponse servletresponse) throws Exception{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Storages> lList = session.createCriteria(Storages.class).list();
		List<TreeStorageBean> tree = new ArrayList<TreeStorageBean>();
		for (Storages lStorage : lList){
			TreeStorageBean lTreeStorageBean = new TreeStorageBean();
			BeanUtilsBean2.getInstance().copyProperties(lTreeStorageBean, lStorage);
			FileSystemStorageConfig lFileSystemStorageConfig = 
				(FileSystemStorageConfig) XMLUtil.newInstance().deserialize(lStorage.getXmlConfig(), FileSystemStorageConfig.class);
			TreeStorageConfigBean lTreeStorageConfigBean = new TreeStorageConfigBean();
			BeanUtilsBean2.getInstance().copyProperties(lTreeStorageConfigBean, lFileSystemStorageConfig);
			lTreeStorageBean.setConfig(lTreeStorageConfigBean);
			tree.add(lTreeStorageBean);
		}
		model.addAttribute("tree", tree);
		return "/page/index.jsp";
	}
}
