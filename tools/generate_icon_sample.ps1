Add-Type -AssemblyName System.Drawing

$width = 96
$height = 96
$bitmap = New-Object System.Drawing.Bitmap $width, $height
$graphics = [System.Drawing.Graphics]::FromImage($bitmap)
$graphics.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::AntiAlias

# Colors
$blueBrush = new-object System.Drawing.SolidBrush([System.Drawing.ColorTranslator]::FromHtml("#003366"))
$orangePen = new-object System.Drawing.Pen([System.Drawing.ColorTranslator]::FromHtml("#FF6600"), 8)

# Transparent Background
$graphics.Clear([System.Drawing.Color]::Transparent)

# Draw Trash Can Body (Blue)
$rect = New-Object System.Drawing.Rectangle 28, 30, 40, 50
$graphics.FillRectangle($blueBrush, $rect)

# Draw Lid (Blue)
$rectLid = New-Object System.Drawing.Rectangle 20, 20, 56, 8
$graphics.FillRectangle($blueBrush, $rectLid)

# Draw X (Orange Accent)
$graphics.DrawLine($orangePen, 38, 40, 58, 70)
$graphics.DrawLine($orangePen, 58, 40, 38, 70)

$outputPath = "C:\Users\aux1\Documents\iconos nuevos soltec\deletelineticket.png"
$bitmap.Save($outputPath, [System.Drawing.Imaging.ImageFormat]::Png)

Write-Host "Icono generado en: $outputPath"
$graphics.Dispose()
$bitmap.Dispose()
