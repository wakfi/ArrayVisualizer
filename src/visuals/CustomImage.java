package visuals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

import main.ArrayVisualizer;
import templates.Visual;
import utils.Highlights;
import utils.Renderer;

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

final public class CustomImage extends Visual {
	private BufferedImage img;
	
	public CustomImage(ArrayVisualizer ArrayVisualizer, String f) throws IOException {
        super(ArrayVisualizer);
		this.setImage(f);
    }
	
	public void setImage(String f) throws IOException {
		this.img = ImageIO.read(new File(f));
	}
	
    public void drawVisual(int[] array, ArrayVisualizer ArrayVisualizer, Renderer Renderer, Highlights Highlights) {
		for(int i = 0; i < ArrayVisualizer.getCurrentLength(); i++) {
			int width = (int) (Renderer.getXScale() * (i + 1)) - Renderer.getOffset();
			
			if(i < Highlights.getFancyFinishPosition() || Highlights.containsPosition(i)) {
				if(ArrayVisualizer.analysisEnabled()) mainRender.setColor(Color.WHITE);
				else mainRender.setColor(Color.BLACK);
				
				mainRender.fillRect(Renderer.getOffset() + 20, 0, width, ArrayVisualizer.windowHeight());
			} else {
				//Cuts the image in respect to each item in the array
				mainRender.drawImage(
					this.img,
					
					Renderer.getOffset() + 20,
					0, 
					Renderer.getOffset() + 20 + width, 
					ArrayVisualizer.windowHeight(),
					
					(int) ((double) this.img.getWidth() / ArrayVisualizer.getCurrentLength() * array[i]),
					0, 
					(int) Math.ceil((double) this.img.getWidth() / ArrayVisualizer.getCurrentLength() * (array[i] + 1)),
					this.img.getHeight(),
					
					null
				);
			}
			
			Renderer.setOffset(Renderer.getOffset() + width);
		}
    }
}