/*
 *  Copyright © 2014 rued97 All Rights Reserved.
 */

package com.github.shoribu_jsn.pc_koshien;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author rued97
 */
public class Toi9 {
	public static void main(String[] args) {
		
		// 入力ってなんや
		String INPUT_FILE_PATH = "c:\\toi9.txt";
		
		File file = new File(INPUT_FILE_PATH);
		力持ち 一番左の力持ち = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			int idx = 0;
			力持ち last力持ち = null;
			while((line = reader.readLine()) != null) {
				if(idx > 0) {
					String data[] = line.split(" ");
					力持ち new力持ち = new 力持ち(idx, Integer.valueOf(data[0]), Integer.valueOf(data[1]));
					if(idx == 1) {
						一番左の力持ち = new力持ち;
					}
					
					new力持ち.set左の力持ち(last力持ち);
					if(last力持ち != null) {
						last力持ち.set右の力持ち(new力持ち);
					}
					last力持ち = new力持ち;
				}
				idx++;
			}
			
		} catch (FileNotFoundException ex) {
			System.out.println("ばーか。");
			return;
		} catch (IOException ex) {
			System.out.println("ばーか。");
			return;
		}
		
		System.out.println(最小の人数求む(一番左の力持ち));
	}

	static int 最小の人数求む(力持ち メソッドが呼ばれた時一番左の力持ち) {
		if(メソッドが呼ばれた時一番左の力持ち == null) {
			return 0;
		}
		// 現在の人数を計算
		int 現在の人数 = 0;
		{
			力持ち target力持ち = メソッドが呼ばれた時一番左の力持ち;
			while(target力持ち != null) {
				現在の人数++;
				target力持ち = target力持ち.get右の力持ち();
			}
		}
		
		// 最少人数探す
		int 最少人数 = 現在の人数;
		{
			力持ち target力持ち = メソッドが呼ばれた時一番左の力持ち;
			while(target力持ち != null) {
				// 左を持つ
				{
					boolean 左の力持ちを持てた = target力持ち.左の力持ちを持つ();
					if(左の力持ちを持てた) {
						力持ち 今一番左の力持ち = メソッドが呼ばれた時一番左の力持ち;
						if(target力持ち.get持ってる力持ち().equals(メソッドが呼ばれた時一番左の力持ち)) {
							今一番左の力持ち = target力持ち;
						}

						int 左の力持ち持った時の人数 = 最小の人数求む(今一番左の力持ち);

						if(左の力持ち持った時の人数 < 最少人数) {
							最少人数 = 左の力持ち持った時の人数;
						}
						target力持ち.力持ちを下ろす();
					}
				}

				// 右を持つ
				{
					boolean 右の力持ちを持てた = target力持ち.右の力持ちを持つ();
					if(右の力持ちを持てた) {

						int 右の力持ち持った時の人数 = 最小の人数求む(メソッドが呼ばれた時一番左の力持ち);

						if(右の力持ち持った時の人数 < 最少人数) {
							最少人数 = 右の力持ち持った時の人数;
						}
						target力持ち.力持ちを下ろす();
					}
				}
				target力持ち	= target力持ち.get右の力持ち();
			}
		}
		
		return 最少人数;
	}
	
	static class 力持ち {
		private int 通し番号;
		private 力持ち 左の力持ち;
		private 力持ち 右の力持ち;
		private int w;
		private int c;
		private 力持ち 持ってる力持ち;
		
		public 力持ち(int 通し番号, int c, int w) {
			this.通し番号 = 通し番号;
			this.c = c;
			this.w = w;
		}
		
		public boolean 左の力持ちを持つ() {
			boolean 持てた = 力持ちを持つ(this.左の力持ち);
			if(持てた) {
				this.左の力持ち = this.左の力持ち.get左の力持ち();
				if(this.左の力持ち != null) {
					this.左の力持ち.set右の力持ち(this);
				}
			}
			return 持てた;
		}
		
		public boolean 右の力持ちを持つ() {
			boolean 持てた = 力持ちを持つ(this.右の力持ち);
			if(持てた) {
				this.右の力持ち = this.右の力持ち.get右の力持ち();
				if(this.右の力持ち != null) {
					this.右の力持ち.set左の力持ち(this);
				}
			}
			return 持てた;
		}
		
		public boolean 力持ちを持つ(力持ち 持つ力持ち) {
			if(持ってる力持ち != null) {
				return false;
			}
			
			if(持つ力持ち == null) {
				return false;
			}
			
			if(this.c < 持つ力持ち.get総重量()) {
				return false;
			}
			
			this.持ってる力持ち = 持つ力持ち;
			return true;
		}
		
		public void 力持ちを下ろす() {
			if(this.持ってる力持ち == null) {
				return;
			}
			
			if(this.equals(this.持ってる力持ち.get右の力持ち())) {
				if(this.左の力持ち != null) {
					this.左の力持ち.set右の力持ち(this.持ってる力持ち);
				}
				this.左の力持ち = this.持ってる力持ち;
			} else if(this.equals(this.持ってる力持ち.get左の力持ち())) {
				if(this.右の力持ち != null) {
					this.右の力持ち.set左の力持ち(this.持ってる力持ち);
				}
				this.右の力持ち = this.持ってる力持ち;
			} else {
				throw new IllegalStateException();
			}
			this.持ってる力持ち = null;
		}
		
		public int get総重量() {
			int 持ってる重量;
			if(this.持ってる力持ち == null) {
				持ってる重量 = 0;
			} else {
				持ってる重量 = this.持ってる力持ち.get総重量();
			}
			return 持ってる重量 + this.w;
		}
		
		public boolean equals(力持ち target) {
			if(target == null) {
				return false;
			}
			return this.通し番号 == target.get通し番号();
		}

		public int get通し番号() {
			return 通し番号;
		}

		public void set通し番号(int 通し番号) {
			this.通し番号 = 通し番号;
		}

		public 力持ち get左の力持ち() {
			return 左の力持ち;
		}

		public void set左の力持ち(力持ち 左の力持ち) {
			this.左の力持ち = 左の力持ち;
		}

		public 力持ち get右の力持ち() {
			return 右の力持ち;
		}

		public void set右の力持ち(力持ち 右の力持ち) {
			this.右の力持ち = 右の力持ち;
		}

		public 力持ち get持ってる力持ち() {
			return 持ってる力持ち;
		}
		
	}
}
