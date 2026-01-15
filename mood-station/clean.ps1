# ==========================================
# WSL 网络调试清理脚本 (Run as Administrator)
# ==========================================

Write-Host "正在开始清理 WSL 端口转发规则..." -ForegroundColor Cyan

# 1. 删除 8000 端口的转发规则 (后端)
# 这里的 IP 不重要，只要端口对就能删
netsh interface portproxy delete v4tov4 listenport=8000 listenaddress=0.0.0.0
if ($?) { Write-Host "✅ 端口 8000 转发规则已删除" -ForegroundColor Green }
else { Write-Host "⚠️ 端口 8000 规则删除失败或不存在" -ForegroundColor Yellow }

# (可选) 如果你想顺便把那个 3000 端口(前端)的规则也删了，可以运行下面这行：
# netsh interface portproxy delete v4tov4 listenport=3000 listenaddress=0.0.0.0

# 2. 删除防火墙规则
Write-Host "正在删除防火墙规则..." -ForegroundColor Cyan
Remove-NetFirewallRule -DisplayName "WSL-FastAPI-8000" -ErrorAction SilentlyContinue

if ($?) { Write-Host "✅ 防火墙规则 'WSL-FastAPI-8000' 已移除" -ForegroundColor Green }
else { Write-Host "ℹ️ 未发现防火墙规则，无需移除" -ForegroundColor Gray }

Write-Host "🎉 清理工作完成！系统已恢复原样。" -ForegroundColor Magenta