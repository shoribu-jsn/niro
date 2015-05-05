/*
 *  Copyright © 2015 rued97 All Rights Reserved.
 */
package com.github.shoribu_jsn.experiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Streamを使ってみる。
 * @author rued97
 */
public class Java8StreamExperiment {
	public static void main(String[] args) {
		System.out.println("-----------------実験開始-----------------");
		List<String> inputDataList = new ArrayList<>();
		inputDataList.add("ぱいなっぷる");
		inputDataList.add("もも");
		inputDataList.add("りんご");
		inputDataList.add("ごきぶり");
	
		System.out.println("--Streamで中身を吐き出してみる--------------------------------");
		// forEachは副作用を与えるための関数らしい
		inputDataList.stream().forEach((String s) -> {System.out.println(s);});
		
		System.out.println("--Streamで中身を吐き出してみる　その2--------------------------------");
		// ラムダ式・・・左辺の型宣言は省略できるよ
		inputDataList.stream().forEach(s -> {System.out.println(s);});
		
		System.out.println("--Streamで中身を吐き出してみる　その3--------------------------------");
		// ラムダ式・・・中身が1行の時は右側の中括弧やreturn（今回はないけど）も省略できるよ
		inputDataList.stream().forEach(s -> System.out.println(s));

		System.out.println("--Streamで中身を吐き出してみる　その4--------------------------------");
		// ラムダ式・・・でも個人的には、左辺の型は見えたほうがわかりやすくね？って思ったり。
		inputDataList.stream().forEach((String s) -> {System.out.println(s);});

		System.out.println("--Streamで中身を吐き出してみる　その5--------------------------------");
		// 今回の場合は、メソッド参照でもいけちゃう。
		inputDataList.stream().forEach(System.out::println);

		System.out.println("--Streamでフィルタしてみる--------------------------------");
		// 文字数が4文字未満のものしかでないよ
		inputDataList.stream()
			.filter((String s) -> s.length() < 4)
			.forEach(System.out::println);

		System.out.println("--Streamでマップもしてみる--------------------------------");
		// 対象文字列のString型を文字数を表すint型に変えちゃってるよ
		inputDataList.stream()
			.filter((String s) -> s.length() < 4)
			.map((String s) -> s.length())
			.forEach(System.out::println);
	
		System.out.println("-----------------実験終了-----------------");
	}
}
