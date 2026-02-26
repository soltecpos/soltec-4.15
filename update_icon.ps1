$sh = New-Object -ComObject WScript.Shell
$lnk = $sh.CreateShortcut("C:\Users\Public\Desktop\SOLTEC POS.lnk")
$lnk.IconLocation = "C:\Users\aux1\Documents\loquedejolatormenta\src\main\resources\com\openbravo\images\iconoprincipal.png"
$lnk.Save()
