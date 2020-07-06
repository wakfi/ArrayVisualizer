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

public enum Distributions {
	LINEAR {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {}
    },
    SIMILAR {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
            int currentLen = ArrayVisualizer.getCurrentLength();
            
            for(int i = 0; i < currentLen - 8; i++) {
                array[i] = currentLen / 2;
                
                if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
            }
            for(int i = currentLen - 8; i < currentLen; i++) {
                array[i] = (int) (Math.random() < 0.5 ? currentLen * 0.75 : currentLen * 0.25);
                
                if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
            }
        }
    },
	ROUNDED {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
            int currentLen = ArrayVisualizer.getCurrentLength();
            double factor = Math.max(currentLen/8d, 1);
			
            for(int i = 0; i < currentLen; i++) {
				array[i] = (int)(factor*(int)(i/factor));
				
				if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
			}
        }
    },
	RANDOM {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
            int currentLen = ArrayVisualizer.getCurrentLength();
			
            for(int i = 0; i < currentLen; i++) {
				array[i] = (int)(Math.random()*currentLen);
				
				if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
			}
        }
    },
	SQUARE {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength();
			
			for(int i = 0; i < currentLen; i++) {
				array[i] = (int)(Math.pow(i, 2)/currentLen);
				
				if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
			}
        }
    },
	SQRT {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength();
			
			for(int i = 0; i < currentLen; i++) {
				array[i] = (int)(Math.sqrt(i)*Math.sqrt(currentLen));
				
				if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
			}
        }
    },
	CUBIC {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength();
			double mid = (currentLen-1)/2d;
			
			for(int i = 0; i < currentLen; i++) {
				array[i] = (int)(Math.pow(i - mid, 3)/Math.pow(mid, 2) + mid);
				
				if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
			}
        }
    },
	QUINTIC {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength();
			double mid = (currentLen-1)/2d;
			
			for(int i = 0; i < currentLen; i++) {
				array[i] = (int)(Math.pow(i - mid, 5)/Math.pow(mid, 4) + mid);
				
				if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
			}
        }
    },
	CUSTOM {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {}
    },
	TWELVETET {
        @Override
        public void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes) {
			int currentLen = ArrayVisualizer.getCurrentLength();
			int note;
			double error = 0.05;
			
			for(int i = 0; i < currentLen; i++) {
				for(note = i; note > 0; note--) {
					double pitch = note / (double) currentLen * (105 - 25) + 25;
					double pitchError = pitch - (int)pitch;
					if(pitchError <= error || pitchError >= 1 - error) {
						array[i] = note;
						break;
					}
				}
				
				if(ArrayVisualizer.shuffleEnabled()) Delays.sleep(1);
			}
		}
    };

    public abstract void initializeArray(int[] array, ArrayVisualizer ArrayVisualizer, Delays Delays, Highlights Highlights, Writes Writes);
}