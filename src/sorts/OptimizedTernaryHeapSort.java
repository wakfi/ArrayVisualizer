package sorts;

import templates.Sort;
import utils.Delays;
import utils.Highlights;
import utils.Reads;
import utils.Writes;

final public class OptimizedTernaryHeapSort extends Sort {
    public OptimizedTernaryHeapSort(Delays delayOps, Highlights markOps, Reads readOps, Writes writeOps) {
        super(delayOps, markOps, readOps, writeOps);
        
        this.setSortPromptID("Optimized Ternary Heap");
        this.setRunAllID("Optimized Ternary Heap Sort");
        this.setReportSortID("Optimized Ternary Heapsort");
        this.setCategory("Selection Sorts");
        this.isComparisonBased(true);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.isBogoSort(false);
    }
    
    // TERNARY HEAP SORT - written by qbit
    // https://codereview.stackexchange.com/questions/63384/binary-heapsort-and-ternary-heapsort-implementation
    
    private int heapSize;
    
    private static int leftBranch(int i) {
        return 3 * i + 1;
    }

    private static int middleBranch(int i) {
        return 3 * i + 2;
    }

    private static int rightBranch(int i) {
        return 3 * i + 3;
    }

    private void maxHeapify(int[] array, int i) {
		int temp = array[i], root = i;
		while(this.leftBranch(root) <= this.heapSize) {
			int leftChild   = OptimizedTernaryHeapSort.leftBranch(root);
			int rightChild  = OptimizedTernaryHeapSort.rightBranch(root);
			int middleChild = OptimizedTernaryHeapSort.middleBranch(root);
			int largest     = leftChild;

			if(rightChild <= heapSize && Reads.compare(array[rightChild], array[largest]) > 0) {
				largest = rightChild;
			}

			if(middleChild <= heapSize && Reads.compare(array[middleChild], array[largest]) > 0) {
				largest = middleChild;
			}

			if(Reads.compare(array[largest], temp) > 0) {
				Writes.write(array, root, array[largest], 1, true, false);
				root = largest;
			} else break;
		}
		
		Writes.write(array, root, temp, 1, true, false);
    }
    
    private void buildMaxTernaryHeap(int[] array, int length) {
        this.heapSize = length - 1;
        for(int i = this.heapSize / 3; i >= 0; i--)
            this.maxHeapify(array, i);
    }
    
    @Override
    public void runSort(int[] array, int length, int bucketCount) {
        this.buildMaxTernaryHeap(array, length);

        for(int i = length - 1; i > 0; i--){
            Writes.swap(array, 0, i, 1, true, false); //add last element on array, i.e heap root

            this.heapSize--; //shrink heap by 1
            this.maxHeapify(array, 0);
        }
    }
}