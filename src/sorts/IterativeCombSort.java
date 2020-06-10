package sorts;

import templates.Sort;
import utils.Delays;
import utils.Highlights;
import utils.Reads;
import utils.Writes;

/*
 * 
The MIT License (MIT)

Copyright (c) 2012 Daniel Imms, http://www.growingwiththeweb.com

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

//Implementation by @aphitorite
final public class IterativeCombSort extends Sort {
    public IterativeCombSort(Delays delayOps, Highlights markOps, Reads readOps, Writes writeOps) {
        super(delayOps, markOps, readOps, writeOps);
        
        this.setSortPromptID("Iterative Comb");
        this.setRunAllID("Iterative Comb Sort");
        this.setReportSortID("Iterative Combsort");
        this.setCategory("Exchange Sorts");
        this.isComparisonBased(true);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.isBogoSort(false);
        
    }

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
		int pow2 = (int)(Math.log(length-1)/Math.log(2));
		
		for(int k = pow2; k >= 0; k--) {
			int pow3 = (int)((Math.log(length) - k*Math.log(2))/Math.log(3));
			
			for(int j = pow3; j >= 0; j--) {
				int gap = (int)(Math.pow(2, k)*Math.pow(3, j));
				
				for(int i = 0; i+gap < length; i++) {
					Highlights.markArray(1, i);
					Highlights.markArray(2, i+gap);
					Delays.sleep(0.25);
					if(Reads.compare(array[i],array[i+gap])==1)
						Writes.swap(array, i, i+gap, 0.75, true, false);
				}
			}
		}
    }
}