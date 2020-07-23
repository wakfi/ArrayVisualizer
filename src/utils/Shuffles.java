package utils;

import main.ArrayVisualizer;

/*
 * 
MIT License

Copyright (c) 2019 w0rthy

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

public enum Shuffles {
    RANDOM {
        // If you want to learn why the random shuffle was changed,
        // I highly encourage you read this. It's quite fascinating:
        // http://datagenetics.com/blog/november42014/index.html
            
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
            int currentLen = ArrayVisualizer.getCurrentLength();
            
            //TODO: Consider separate method
            for(int i = 0; i < currentLen; i++){
                int randomIndex = (int) (Math.random() * (currentLen - i)) + i;
                Writes.swap(array, i, randomIndex, 0, true, false);
                
                if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
            }
        }
    },
    ALMOST {
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
            int currentLen = ArrayVisualizer.getCurrentLength();
            
            for(int i = 0; i < Math.max(currentLen / 20, 1); i++){
                Writes.swap(array, (int)(Math.random()*currentLen), (int)(Math.random()*currentLen), 0, true, false);
                if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(2);
            }
            
            /*
            int step = (int) Math.sqrt(currentLen);
            
            //TODO: *Strongly* consider randomSwap method
            for(int i = 0; i < currentLen; i += step){
                int randomIndex = (int) (Math.random() * step);
                randomIndex = Math.max(randomIndex, 1);
                randomIndex = Math.min(randomIndex, currentLen - i - 1);
                Writes.swap(array, i, i + randomIndex, 0, true, false);

                if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(2);
            }
            */
        }
    },
    ALREADY {
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
            int currentLen = ArrayVisualizer.getCurrentLength();
            
            for(int i = 0; i < currentLen; i++) {
                if(ArrayVisualizer.shuffleEnabled()) {
                    Highlights.markArray(1, i);
                    Delays.sleep(1);
                }
            }
        }
    },
	NAIVE {
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
            int currentLen = ArrayVisualizer.getCurrentLength();
            
            for(int i = 0; i < currentLen; i++){
                Writes.swap(array, i, (int)(Math.random()*(currentLen)), 0, true, false);
                
                if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
            }
        }
    },
	TAIL {
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength();
			int j = 0, k = currentLen;
			int[] temp = new int[currentLen];
            
            for(int i = 0; j < k; i++){
				if(Math.random() < 1/7d)
					temp[--k] = array[i];
				else
					temp[j++] = array[i];
            }
			
			for(int i = 0; i < currentLen; i++)
				array[i] = temp[i];
			
			for(int i = k; i < currentLen; i++){
                int randomIndex = (int) (Math.random() * (currentLen - i)) + i;
                Writes.swap(array, i, randomIndex, 0, true, false);
                
                if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
            }
        }
    },
	ORGAN {
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength();
			int[] temp = new int[currentLen];
            for(int i = 0, j = 0; i < currentLen; i+=2){
				temp[j++] = array[i];
            }
			for(int i = 1, j = currentLen; i < currentLen; i+=2) {
				temp[--j] = array[i];
			}
			for(int i = 0; i < currentLen; i++){
                Writes.write(array, i, temp[i], 0, true, false);
                
                if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
            }
			/*
			Double Slope:
			int currentLen = ArrayVisualizer.getCurrentLength();
            for(int i = 0; i < currentLen/2; i+=2){
				Writes.swap(array, i, currentLen - (currentLen+1)%2 - 1 - i, 0, true, false);
                
                if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
            }
			*/
        }
    },
	SAW {
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength();
            int sawLen = Math.max((int)Math.ceil(currentLen/10d), 2);
			int i;
			
            for(i = 0; i+sawLen-1 < currentLen; i+=sawLen)
				Writes.reversal(array, i, i+sawLen-1, ArrayVisualizer.shuffleEnabled() ? 1 : 0, true, false);
			
			if(i < currentLen)
				Writes.reversal(array, i, currentLen-1, ArrayVisualizer.shuffleEnabled() ? 1 : 0, true, false);
        }
    },
	WEAVE {
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength();
			double delay = ArrayVisualizer.shuffleEnabled() ? 1 : 0;
			// :(
			weaveRec(array, 0, currentLen, 1, delay, Delays, Highlights);
		}
		
		private void weaveRec(int[] array, int pos, int len, int gap, double delay, Delays Delays, Highlights Highlights) {
			if(pos+gap >= len)
				return;
			
			int i, j, k;
			boolean up = true;
			int[] temp = new int[len];
			
			for(i = 0; i < len/2; i+=gap) {
				temp[pos+i] = array[pos+i];
			}
			
			j = pos;
			k = pos + i;
			for(i = pos; i < pos+len; i+=gap) {
				Highlights.markArray(1, i);
				Highlights.markArray(2, k);
				
				if(up) {
					array[i] = array[k];
					k += gap;
				} else {
					array[i] = temp[j];
					j += gap;
				}
				
				up = !up;
			}
			Delays.sleep(delay);
			
			weaveRec(array, pos+gap, len, 2*gap, delay, Delays, Highlights);
			weaveRec(array, pos, len, 2*gap, delay, Delays, Highlights);
		}
    },
	HALVES {
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength(), k = 0;
			int[] temp = new int[currentLen];
			
			for(int j = 0; j < 2; j++)
				for(int i = j; i < currentLen; i+=2)
					temp[k++] = array[i];
				
			for(int i = 0; i < currentLen; i++)
				Writes.write(array, i, temp[i], ArrayVisualizer.shuffleEnabled() ? 1 : 0, true, false);
		}
    },
	FIFTHS {
        @Override
        public void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength(), k = 0;
			int[] temp = new int[currentLen];
			
			for(int j = 0; j < 5; j++)
				for(int i = j; i < currentLen; i+=5)
					temp[k++] = array[i];
				
			for(int i = 0; i < currentLen; i++)
				Writes.write(array, i, temp[i], ArrayVisualizer.shuffleEnabled() ? 1 : 0, true, false);
		}
    };

    public abstract void shuffleArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes);
}