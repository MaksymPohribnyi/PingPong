import javax.swing.JApplet;

public class HelloApllet extends JApplet {
	public void paint1(java.awt.Graphics graphics) {
		graphics.drawString("������, ���!", 70, 40);
	}

	public static void main(String[] args) {

		HelloApllet myApplet = new HelloApllet();

	}

}
