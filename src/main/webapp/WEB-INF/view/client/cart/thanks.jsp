<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cảm ơn đã đặt hàng</title>
    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/css/bootstrap.min.css" rel="stylesheet">
</head>
<style>
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f3f3f3;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .container {
        text-align: center;
    }

    .content {
        background-color: #ffffff;
        padding: 40px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        max-width: 600px;
        margin: auto;
    }

    h1 {
        color: #333333;
        font-size: 2.5rem;
        margin-bottom: 20px;
    }

    p {
        color: #666666;
        font-size: 1.2rem;
        line-height: 1.6;
        margin-bottom: 10px;
    }

</style>
<body>
<div class="container">
    <div class="content">
        <h1>Cảm ơn bạn đã đặt hàng!</h1>
        <p>Xin chào! Chúng tôi đã nhận được đơn hàng của bạn và sẽ xử lý sớm nhất có thể.</p>
        <p>Cảm ơn bạn đã lựa chọn sản phẩm của chúng tôi.</p>
    </div>
    <div class="mt-4">
        <i class="fas fa-arrow-left"></i>
        <a href="/">Quay lại trang chủ</a>
    </div>
</div>
</body>
</html>
