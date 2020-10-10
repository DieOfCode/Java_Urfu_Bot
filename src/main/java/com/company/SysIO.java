package com.company;

import java.util.Scanner;

public class SysIO implements InputOutput {
    public String Input() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void Output(String out) {
        System.out.println(out);
    }
}
