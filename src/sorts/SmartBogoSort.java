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
        this.setRunAllID("Permutation Sort");
        this.setReportSortID("Permutationsort");
        this.setCategory("Distributive Sorts");
        this.isComparisonBased(false);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(true);
        this.setUnreasonableLimit(12);
        this.isBogoSort(true);
    }
	
	//deterministic bogo sort to run in guaranteed O(n*n!) worst case
	//heap's algorithm from wikipedia
    @Override
    public void runSort(int[] array, int currentLen, int bucketCount) {
		//c is an encoding of the stack state. c[k] encodes the for-loop counter for when generate(k+1, A) is called
		int[] c = new int[currentLen];

		if(this.bogoIsSorted(array, currentLen)) return;
		
		//i acts similarly to the stack pointer
		int i = 0;
		while(i < currentLen) {
			if(c[i] < i) {
				if(i%2 == 0) {
					Writes.swap(array, 0, i, 0, false, false);
				}
				else {
					Writes.swap(array, c[i], i, 0, false, false);
				}
				if(this.bogoIsSorted(array, currentLen)) return;
				//Swap has occurred ending the for-loop. Simulate the increment of the for-loop counter
				Writes.write(c, i, c[i]+1, 0, false, true);
				//Simulate recursive call reaching the base case by bringing the pointer to the base case analog in the array
				i = 0;
			}
			else {
				//Calling generate(i+1, A) has ended as the for-loop terminated. Reset the state and simulate popping the stack by incrementing the pointer.
				Writes.write(c, i, 0, 0, false, true);
				i++;
			}
		}
    }
}