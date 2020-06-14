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

final public class RecursiveWeavedBitonicSort extends Sort {
    public RecursiveWeavedBitonicSort(Delays delayOps, Highlights markOps, Reads readOps, Writes writeOps) {
        super(delayOps, markOps, readOps, writeOps);
        
        this.setSortPromptID("Rec. Weaved Bitonic");
        this.setRunAllID("Recursive Weaved Bitonic Sort");
        this.setReportSortID("Recursive Weaved Bitonic Sort");
        this.setCategory("Concurrent Sorts");
        this.isComparisonBased(true);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.isBogoSort(false);
    }
	
	private void circle(int[] array, int pos, int len, int trueLen, int gap) {
		if(len < 2)
			return;
		
		for(int i = 0; 2*i < (len-1)*gap; i+=gap) {
			if(pos+(len-1)*gap-i < trueLen) {
				Highlights.markArray(1, pos+i);
				Highlights.markArray(2, pos+(len-1)*gap-i);
				Delays.sleep(0.5);
				if(Reads.compare(array[pos+i], array[pos+(len-1)*gap-i]) == 1)
					Writes.swap(array, pos+i, pos+(len-1)*gap-i, 0.5, true, false);
			}
		}
		
		this.circle(array, pos, len/2, trueLen, gap);
		this.circle(array, pos+len*gap/2, len/2, trueLen, gap);
	}
	
	private void weaveCircle(int[] array, int pos, int len, int trueLen, int gap) {
		if(len < 2)
			return;
		
		this.weaveCircle(array, pos, len/2, trueLen, 2*gap);
		this.weaveCircle(array, pos+gap, len/2, trueLen, 2*gap);
		
		this.circle(array, pos, len, trueLen, gap);
	}

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
		this.weaveCircle(array, 0, (int)Math.pow(2, Math.ceil(Math.log(length)/Math.log(2))), length, 1);
    }
}