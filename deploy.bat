chcp 65001
@echo off

REM 进入项目根目录（根据你的项目路径修改）
cd /d %~dp0

echo ===========================
echo 1. 使用 Maven 打包项目（跳过测试）
call mvn clean package -DskipTests
echo Maven 命令返回码：%errorlevel%

if errorlevel 1 (
    echo Maven 打包失败，终止部署！
    pause
    exit /b 1
)

echo ===========================
echo 2. 停止并移除 auth-bootstrap 容器（如果存在）
REM 尝试停止 auth-bootstrap 容器，如果容器不存在或已停止，则忽略错误
call docker stop auth-bootstrap-container >NUL 2>&1
REM 尝试移除 auth-bootstrap 容器，如果容器不存在，则忽略错误
call docker rm auth-bootstrap-container >NUL 2>&1

echo ===========================
echo 3. 重新构建 auth-bootstrap 镜像并启动 auth-bootstrap 服务
REM --build 确保构建最新的 auth-bootstrap 镜像
REM -d 后台运行
REM auth-bootstrap 指定只操作 auth-bootstrap 服务
call docker compose up -d --build auth-bootstrap

if errorlevel 1 (
    echo Docker Compose 启动 auth-bootstrap 失败，终止部署！
    pause
    exit /b 1
)

echo ===========================
echo 部署完成！

pause