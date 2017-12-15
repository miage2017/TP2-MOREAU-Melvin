package fr.unice.miage.sd.tp3;

import java.util.Arrays;

public class TriBitonique {

	public static void bitonicSort(int[] a) {
		if (a.length >= 2) {
			// découpage
			int[] left = Arrays.copyOfRange(a, 0, a.length / 2);
			int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);

			// tri des deux parties
			bitonicSort(left);
			bitonicSort(right);

			// fusion des parties
			fusionner(left, right, a);
		}

	}

	public static void bitonicSort(int[] a, int nCore) {
		if (nCore > 1) {
			int[] left = Arrays.copyOfRange(a, 0, a.length / 2);
			int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);

			Thread th1 = new Thread(new Runnable() {
				@Override
				public void run() {
					bitonicSort(left, nCore / 2);
				}
			});
			Thread th2 = new Thread(new Runnable() {
				@Override
				public void run() {
					bitonicSort(right, nCore / 2);
				}
			});
			th1.start();
			th2.start();
			try {
				th1.join();
				th2.join();
				fusionner(left, right, a);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			bitonicSort(a);
		}
	}

	private static void fusionner(int[] left, int[] right, int[] a) {
		int index1 = 0, index2 = 0, index3 = 0;

		while (index3 < left.length + right.length - 1) {
			if (left[index1] < right[index2]) {
				a[index3] = left[index1];
				index1++;
				index3++;
				if (index1 > left.length - 1) {
					while (index2 < right.length) {
						a[index3] = right[index2];
						index2++;
						index3++;
					}
				}
			} else if (left[index1] >= right[index2]) {
				a[index3] = right[index2];
				index2++;
				index3++;
				if (index2 > right.length - 1) {
					while (index1 < left.length) {
						a[index3] = left[index1];
						index1++;
						index3++;
					}
				}
			}
		}
	}

	public static void affTab(int[] tri) {
		String aff = "[";
		for (int i = 0; i < tri.length; i++) {
			if (i < tri.length - 1) {
				aff += tri[i] + ",";
			} else {
				aff += tri[i];
			}
		}
		aff += "]";
		System.out.println(aff);
	}

	public static void main(String[] args) {
		int[] tab = { 8, 2, 7, 12, 5, 1, 15, 20 };
		int[] tab1 = { 5, 4, 6, 12, 8, 1, 11, 60, 5, 9 };
		bitonicSort(tab);
		bitonicSort(tab1, 2);
		affTab(tab);
		affTab(tab1);
	}
}
