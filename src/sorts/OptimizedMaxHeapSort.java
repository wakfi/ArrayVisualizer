package sorts;

import templates.OptimizedHeapSorting;
import utils.Delays;
import utils.Highlights;
import utils.Reads;
import utils.Writes;

/*
 * 
Copyright (c) rosettacode.org.
Permission is granted to copy, distribute and/or modify this document
under the terms of the GNU Free Documentation License, Version 1.2
or any later version published by the Free Software Foundation;
with no Invariant Sections, no Front-Cover Texts, and no Back-Cover
Texts.  A copy of the license is included in the section entitled "GNU
Free Documentation License".
 *
 */

final public class OptimizedMaxHeapSort extends OptimizedHeapSorting {
    public OptimizedMaxHeapSort(Delays delayOps, Highlights markOps, Reads readOps, Writes writeOps) {
        super(delayOps, markOps, readOps, writeOps);
        
        this.setSortPromptID("Optimized Max Heap");
        this.setRunAllID("Optimized Max Heap Sort");
        this.setReportSortID("Optimized Heapsort");
        this.setCategory("Selection Sorts");
        this.isComparisonBased(true);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.isBogoSort(false);
    }

    public void customHeapSort(int[] array, int start, int length, double sleep) {
        this.heapSort(array, start, length, sleep, true);
    }

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
        this.heapSort(array, 0, length, 1, true);
    }
}