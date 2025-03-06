// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import javax.swing.filechooser.FileSystemView;

class WindowsAltFileSystemView extends FileSystemView
{
    public static final String EXCEPTION_CONTAINING_DIR_NULL = "AltFileSystemView.exception.containing.dir.null";
    public static final String EXCEPTION_DIRECTORY_ALREADY_EXISTS = "AltFileSystemView.exception.directory.already.exists";
    public static final String NEW_FOLDER_NAME = " AltFileSystemView.new.folder.name";
    public static final String FLOPPY_DRIVE = "AltFileSystemView.floppy.drive";
    
    public boolean isRoot(final File obj) {
        if (!obj.isAbsolute()) {
            return false;
        }
        final String parent = obj.getParent();
        return parent == null || new File(parent).equals(obj);
    }
    
    public File createNewFolder(final File file) throws IOException {
        if (file == null) {
            throw new IOException(Resources.getString("AltFileSystemView.exception.containing.dir.null"));
        }
        File file2 = this.createFileObject(file, Resources.getString(" AltFileSystemView.new.folder.name"));
        for (int i = 2; file2.exists() && i < 100; file2 = this.createFileObject(file, Resources.getString(" AltFileSystemView.new.folder.name") + " (" + i + ')'), ++i) {}
        if (file2.exists()) {
            throw new IOException(Resources.formatMessage("AltFileSystemView.exception.directory.already.exists", new Object[] { file2.getAbsolutePath() }));
        }
        file2.mkdirs();
        return file2;
    }
    
    public boolean isHiddenFile(final File file) {
        return false;
    }
    
    public File[] getRoots() {
        final ArrayList list = new ArrayList<FileSystemRoot>();
        list.add(new FileSystemRoot(Resources.getString("AltFileSystemView.floppy.drive") + "\\"));
        for (char c = 'C'; c <= 'Z'; ++c) {
            final FileSystemRoot fileSystemRoot = new FileSystemRoot(new String(new char[] { c, ':', '\\' }));
            if (fileSystemRoot != null && fileSystemRoot.exists()) {
                list.add(fileSystemRoot);
            }
        }
        final File[] array = new File[list.size()];
        list.toArray(array);
        return array;
    }
    
    class FileSystemRoot extends File
    {
        public FileSystemRoot(final File parent) {
            super(parent, "");
        }
        
        public FileSystemRoot(final String pathname) {
            super(pathname);
        }
        
        public boolean isDirectory() {
            return true;
        }
    }
}
