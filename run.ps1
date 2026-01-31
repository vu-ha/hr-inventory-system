# Script chạy ứng dụng HR Inventory System
# Tự động dừng process đang sử dụng port 8082 trước khi chạy

Write-Host "=== HR Inventory System - Startup Script ===" -ForegroundColor Cyan
Write-Host ""

# Dừng tất cả process Java đang chạy để tránh xung đột
Write-Host "Stopping all Java processes to avoid conflicts..." -ForegroundColor Yellow
$javaProcesses = Get-Process -Name java -ErrorAction SilentlyContinue
if ($javaProcesses) {
    Write-Host "Found $($javaProcesses.Count) Java process(es). Stopping..." -ForegroundColor Yellow
    $javaProcesses | Stop-Process -Force -ErrorAction SilentlyContinue
    Start-Sleep -Seconds 2
    Write-Host "Java processes stopped." -ForegroundColor Green
} else {
    Write-Host "No Java processes found." -ForegroundColor Green
}

# Kiểm tra và dừng process đang sử dụng port 8082 (nếu còn)
Write-Host "Checking port 8082..." -ForegroundColor Yellow
$portProcesses = netstat -ano | findstr :8082 | Select-String "LISTENING"

if ($portProcesses) {
    $processIds = @()
    foreach ($process in $portProcesses) {
        $processId = ($process -split '\s+')[-1]
        if ($processId -and $processId -match '^\d+$') {
            $processIds += $processId
        }
    }
    
    if ($processIds.Count -gt 0) {
        Write-Host "Found $($processIds.Count) process(es) still using port 8082: $($processIds -join ', ')" -ForegroundColor Yellow
        Write-Host "Stopping processes..." -ForegroundColor Yellow
        foreach ($processId in $processIds) {
            Stop-Process -Id $processId -Force -ErrorAction SilentlyContinue
        }
        Start-Sleep -Seconds 2
        Write-Host "Processes stopped." -ForegroundColor Green
    }
} else {
    Write-Host "Port 8082 is available." -ForegroundColor Green
}

Write-Host ""
Write-Host "Starting application..." -ForegroundColor Cyan
Write-Host ""

# Chạy ứng dụng
.\mvnw.cmd spring-boot:run
