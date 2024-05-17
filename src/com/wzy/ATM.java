package com.wzy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ATM {
    private final ArrayList<Account> accountList = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private ArrayList<Integer> accountCountList = new ArrayList<>();
    int index;

    private void createAccount() {
        System.out.println("请输入您的姓名:");
        String name = sc.next();

        System.out.println("请输入您的性别:");
        String sex;
        while (true) {
            String input = sc.next();
            if (input.equals("男") || input.equals("女")) {
                sex = input;
                break;
            } else {
                System.out.println("您输入的性别不在男女范围内，请确认后重新输入：");
            }
        }

        System.out.println("请输入您的密码:");
        int password = sc.nextInt();

        double money;
        while (true) {
            System.out.println("请输入您的存款金额:");
            money = sc.nextDouble();
            if (money >= 0) {
                System.out.println("存款成功");
                break;
            }else{
                System.out.println("输入金额有误，请确认后重新输入(最低存入0元)：");
            }
        }


        if(!accountCountList.contains(0)){
            accountCountList.add(1);
            index = accountCountList.size();
        }else{
            index = accountCountList.indexOf(0) + 1;
        }
        Account account = new Account(index, name, sex, password, money);
        System.out.println("您的卡号是" + account.getNumber() + ",请务必牢记,忘记的话钱就归我咯~~");
        accountList.add(account);
    }

    private void logIn() {
        if(accountList.isEmpty()){
            System.out.println("当前系统不存在用户，请先开户");
            return;
        }
        while (true) {
            System.out.println("=========登录=========");
            System.out.println("请输入您的卡号:");
            String number = sc.next();
            Account foundAccount = null;
            for (Account account : accountList) {
                if (account.getNumber().equals(number)) {
                    foundAccount = account;
                    break;
                }
            }
            if (foundAccount != null) {
                System.out.println("请输入您的密码:");
                compare(foundAccount);
                return;
                } else {
                System.out.println("账户不存在，请确认后重新输入");
            }
        }
    }

    private void compare(Account account) {
        while(true) {
            int password = sc.nextInt();
            if (password == account.getPassword()) {
                System.out.println("登陆成功！！！");
                twoStart(account);
                return;
            } else {
                System.out.println("密码错误，请重新输入:");
            }
        }
    }

    private void removeId(String number){
        int a = Integer.parseInt(number.substring(10));
        accountCountList.set(a - 1 , 0);
    }

    private void compareMoney(double money,Account account,Account account1) {
        if (money > account.getMoney()) {
            System.out.println("穷逼你有那么多钱吗就转这么多。。。");
            twoStart(account);
        } else if(money > 10000) {
            System.out.println("金额过大，最高转账一万元");
            twoStart(account);
        }else{
            System.out.println("请输入您的密码：");
            int password = sc.nextInt();
            if (password == account.getPassword()) {
                account1.setMoney(account1.getMoney() + money);
                account.setMoney(account.getMoney() - money);
                System.out.println("转账成功！！！");
                twoStart(account);
            } else {
                System.out.println("密码错误，请重新输入！！");
            }
        }
    }

    private void twoStart(Account account) {
        while(true) {
            System.out.println("=========ATM账户操作系统=========");
            System.out.println("1、查询账户");
            System.out.println("2、存款");
            System.out.println("3、取款");
            System.out.println("4、转账");
            System.out.println("5、修改密码");
            System.out.println("6、注销账户");
            System.out.println("7、退出");
            System.out.println("请输入您的需求：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    System.out.println("姓名:" + account.getName());
                    System.out.println("性别:" + account.getSex());
                    System.out.println("账户余额:" + account.getMoney());
                    break;
                case 2:
                    System.out.println("请输入您的存款金额：");
                    double moneyInput = sc.nextDouble();
                    account.setMoney(account.getMoney() + moneyInput);
                    System.out.println("存款成功！！！");
                    break;
                case 3:
                    System.out.println("请输入您的取款金额：");
                    double moneyOutput = sc.nextDouble();
                    account.setMoney(account.getMoney() - moneyOutput);
                    System.out.println("取款成功！！！");
                    break;
                case 4:
                    transferMoney(account);
                    break;
                case 5:
                    changePassword(account);
                    break;
                case 7:
                    System.out.println("退出成功！下次再来！");
                    return;
                case 6:
                    removeAccount(account.getNumber());
                    return;
            }
        }
    }

    private void changePassword(Account account) {
        int password1 = 0;
        while (password1 != 1) {
            System.out.println("请输入您的新密码:");
            password1 = sc.nextInt();
            System.out.println("请确认您的新密码：");
            int password2 = sc.nextInt();
            if(password2 == account.getPassword()){
                System.out.println("密码与原密码一致，请重新输入：");
            }else {
                if (password1 == password2) {
                    account.setPassword(password1);
                    System.out.println("密码修改成功");
                    break;
                } else {
                    System.out.println("两次密码输入不一致，请重新输入(按1退出)：");
                }
            }
        }
    }

    public void transferMoney(Account account){
        if(accountList.size() == 1){
            System.out.println("账户目前仅有您一位贵宾，别转账了求求你了");
            return;
        }
        System.out.println("单次转账限额:" + account.limit);
        System.out.println("请输入目标账户卡号：");
        String number = sc.next();
        Account targetAccount = null;
        for (Account account1 : accountList) {
            if (account1.getNumber().equals(number)) {
                targetAccount = account1;
                break;
            }
        }
        if (targetAccount != null) {
            System.out.println("请输入转账金额：");
            double money = sc.nextDouble();
            compareMoney(money, account, targetAccount);
        } else {
            System.out.println("账户不存在，请确认后重新输入");
        }
    }

    private void removeAccount(String id){
        System.out.println("您确认要销户吗？(输入y确定销户)");
        String n = sc.next();
        if (!n.equals("y")) {
            return;
        }

        Account targetAccount = null;
        for (Account account : accountList) {
            if (account.getNumber().equals(id)) {
                targetAccount = account;
                break;
            }
        }

        if (targetAccount != null) {
            accountList.remove(targetAccount);
            System.out.println("删除成功");
            removeId(id);
        }
    }


    public void oneStart(){
        while (true) {
            System.out.println("=========欢迎进入ATM系统==========");
            System.out.println("1、用户登录");
            System.out.println("2、用户开户");
            System.out.println("3、退出系统");
            System.out.println("请输入您的需求:");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    logIn();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("操作不存在,请重新输入：");
            }
        }
    }
}


