package sorts;

import templates.BogoSorting;
import utils.Delays;
import utils.Highlights;
import utils.Reads;
import utils.Writes;

/*
 * 
MIT License

Copyright (c) 2020 aphitorite

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

final public class SmartBogoSort extends BogoSorting {
    public SmartBogoSort(Delays delayOps, Highlights markOps, Reads readOps, Writes writeOps) {
        super(delayOps, markOps, readOps, writeOps);
        
        this.setSortPromptID("Smart Bogo");
        this.setRunAllID("Deterministic Bogo (Permutation) Sort");
        this.setReportSortID("Permutationsort");
        this.setCategory("Distributive Sorts");
        this.isComparisonBased(false);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(true);
        this.setUnreasonableLimit(16);
        this.isBogoSort(true);
    }

    @Override
    public void runSort(int[] array, int currentLen, int bucketCount) {
		//Counts the permutations with n digits (max value only needs to be n!)
		int[] pNum = new int[currentLen];
		int i, j;
		
		while(!this.bogoIsSorted(array, currentLen)) {
			//Increments the pNum count through a factorial counting system
            for(i = 1; pNum[i] == i; i++)
				Writes.write(pNum, i, 0, 0, false, true);
			Writes.write(pNum, i, pNum[i]+1, 0, false, true);
			
			//Makes the minimum amount of swaps back for the next step
			for(j = 1; j <= i; j++) {
				if(pNum[j] != 1) {
					if(pNum[j] == 0)
						Writes.swap(array, j, j-1, 0, false, false);
					else
						Writes.swap(array, j, pNum[j]-2, 0, false, false);
				}
			}
			
			//Makes the minimum amount of swaps to generate current permutation
			for(j = i; j > 0; j--)
				if(pNum[j] != 0)
					Writes.swap(array, j, pNum[j]-1, 0, false, false);
        }
    }
}