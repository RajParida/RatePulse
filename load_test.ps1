$success = 0
$rate = 0
$other = 0
$errors = 0
for ($i = 1; $i -le 80; $i++) {
    try {
        $r = Invoke-WebRequest -Uri 'http://127.0.0.1:8080/api/data' -Headers @{ 'X-API-KEY' = 'internship-key' } -UseBasicParsing -Method Get -TimeoutSec 5
        if ($r.StatusCode -eq 200) { $success++ }
        elseif ($r.StatusCode -eq 429) { $rate++ }
        else { $other++ }
    }
    catch {
        if ($_.Exception.Response -and $_.Exception.Response.StatusCode.value__ -eq 429) { $rate++ }
        elseif ($_.Exception.Response) { $other++ }
        else { $errors++ }
    }
}
Write-Host "Success=$success RateLimited=$rate Other=$other Errors=$errors"
