package sorts;

import templates.Sort;
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

final public class IterativeWeavedBitonicSort extends Sort {
    public IterativeWeavedBitonicSort(Delays delayOps, Highlights markOps, Reads readOps, Writes writeOps) {
        super(delayOps, markOps, readOps, writeOps);
        
        this.setSortPromptID("Iter. Weaved Bitonic");
        this.setRunAllID("Iterative Weaved Bitonic Sort");
        this.setReportSortID("Iterative Weaved Bitonic Sort");
        this.setCategory("Concurrent Sorts");
        this.isComparisonBased(true);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.isBogoSort(false);
    }
	
	private void iterativeCircle(int[] array, int pos, int len, int trueLen, int divisions) {
		int i = 0, d = (len/2)/divisions;
		
		for(int j = len-d; j >= len/2; j-=d) {
			for(int k = 0; k < d; k++) {
				if(pos+j+k < trueLen) {
					Highlights.markArray(1, pos+i);
					Highlights.markArray(2, pos+j+k);
					Delays.sleep(0.5);
					if(Reads.compare(array[pos+i], array[pos+j+k]) == 1)
						Writes.swap(array, pos+i, pos+j+k, 0.5, true, false);
				}
				i++;
			}
		}
	}

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
		int paddedLength = (int)Math.pow(2, Math.ceil(Math.log(length)/Math.log(2)));
		
		for(int i = 1; i < paddedLength; i*=2)
			for(int j = 1; j <= i; j*=2)
				for(int k = 0; k < paddedLength; k += paddedLength/j)
					this.iterativeCircle(array, k, paddedLength/j, length, i/j);
    }
}