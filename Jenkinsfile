/*
 Jenkins 流水线脚本（推荐版）：
 1）使用 Maven 构建；
 2）只跑 ui-project-demo 模块的用例（-pl），自动构建依赖模块（-am）；
 3）不在 Maven 里生成 Allure 报告，只产出 allure-results；
 4）由 Jenkins Allure 插件读取 ui-project-demo/target/allure-results 生成/展示报告。
*/
pipeline {
    agent any

    tools {
        // 注意：这里的名字要和 Jenkins 全局工具配置中的名称一致
        jdk 'jdk21'            // Jenkins 全局配置的 JDK 名称
        maven 'maven-3.9'      // Jenkins 全局配置的 Maven 名称
    }

    stages {
        stage('Checkout') {
            steps {
                // 从源码管理（Git 等）检出当前工程
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // -pl 只构建、测试 ui-project-demo 模块
                // -am 自动构建它依赖的模块（ui-framework-core、ui-framework-testng）
                // clean 确保每次构建前清理上一次的编译/测试结果
                sh 'mvn -B clean test -pl ui-project-demo -am'
            }
        }
    }

    post {
        always {
            // 无论构建成功或失败，都归档 allure-results 原始结果文件；
            // Jenkins Allure 插件会从这里读取数据并生成可视化报告。
            archiveArtifacts artifacts: 'ui-project-demo/target/allure-results/**', fingerprint: true

            // 如果你想在 Jenkins pipeline 层再加一层企业微信/钉钉通知，也可以在这里写 shell 或 HTTP 调用。
            // 但目前你的框架已经在 SuiteListener 里发通知了，这里可以保持空。
        }
    }
}
