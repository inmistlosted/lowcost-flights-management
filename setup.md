## Запуск лабораторної
1. Відкрити лабораторну у IntelliJ IDEA
1. Заімпортувати всі maven залежності
1. На завчасно встановленому PostgreSQL сервері створити нову базу даних з назвою lowcost1
1. У файлі [DBConnection](./src/main/java/app/dao/DBConnection.java)
встановити свій пароль до PostgreSQL сервера
1. У терміналі виконати команду 'mvn liquibase:update' для розгортки бази даних 
1. Додати Tomcat Server у конфігурації запуску проекту
1. Запустити проект на Tomcat Server
1. Перейти у браузері по адресі http://localhost:8080/