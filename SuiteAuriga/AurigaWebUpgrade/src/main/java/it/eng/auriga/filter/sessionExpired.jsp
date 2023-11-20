<html>
<head>
<title>Login</title>
</head>
<body>
<form method="POST" action="j_security_check">
  <table>
    <tr>
      <td align="right">Username:</td>
      <td align="left"><input type="text" name="j_username"></td>
    </tr>
    <tr>
      <td align="right">Password:</td>
      <td align="left"><input type="password" name="j_password"></td>
    </tr>
    <tr>
      <td align="right"><input type="submit" value="Log In"></td>
      <td align="left"><input type="reset"></td>
    </tr>
  </table>
</form>
<SCRIPT>//'"]]>>isc_loginRequired
//
// Embed this whole script block VERBATIM into your login page to enable
// SmartClient RPC relogin.

while (!window.isc && document.domain.indexOf(".") != -1) {
    try {
	
        if (parent.isc == null) {
            document.domain = document.domain.replace(/.*?\./, '');
            continue;
        } 
        break;
    } catch (e) {
        document.domain = document.domain.replace(/.*?\./, '');
    }
}

var isc = top.isc ? top.isc : window.opener ? window.opener.isc : null;
if (isc) isc.RPCManager.delayCall("handleLoginRequired", [window]);
</SCRIPT>
</body>
</html>