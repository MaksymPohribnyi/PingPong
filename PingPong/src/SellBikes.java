import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SellBikes implements ActionListener {

	JLabel modelLabel;
	JLabel amountLabel;
	JLabel informLabel;
	Button buttonMakeOrder;
	JTextField fieldModel;
	JTextField fieldAmount;

	String bikeModel = "";
	int amount = 0;

	public SellBikes() {

		JPanel windowContent = new JPanel();
		BorderLayout bl = new BorderLayout();
		windowContent.setLayout(bl);

		JPanel northPanel = new JPanel();
		GridLayout gl = new GridLayout(2, 2);
		northPanel.setLayout(gl);

		informLabel = new JLabel("");
		modelLabel = new JLabel("Choose your bike model:");
		amountLabel = new JLabel("Enter amount of bikes:");

		fieldModel = new JTextField();
		fieldAmount = new JTextField();

		northPanel.add(modelLabel);
		northPanel.add(amountLabel);
		northPanel.add(fieldModel);
		northPanel.add(fieldAmount);

		windowContent.add("North", northPanel);
		windowContent.add("Center", informLabel);

		buttonMakeOrder = new Button("Make an order");
		buttonMakeOrder.setBackground(Color.green);
		buttonMakeOrder.addActionListener(this);
		windowContent.add(buttonMakeOrder, "South");

		JFrame frame = new JFrame("Make order of your bike:");
		frame.setContentPane(windowContent);

		frame.setSize(300, 150);
		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {

		Button clickedButton = (Button) e.getSource();

		bikeModel = fieldModel.getText();
		String bikeAmount = fieldAmount.getText();

		try {
			amount = Integer.parseInt(bikeAmount);
			checkOrder(bikeModel, amount);
			informLabel.setText("Your order has been placed");
		} catch (TooManyBikesException e2) {
			informLabel.setText(e2.getMessage());
		} catch (Exception e2) {
			informLabel.setText(e2.getMessage());
		}

	}

	public void checkOrder(String model, int quantity) throws TooManyBikesException {

		if (model.equals("BMX") && quantity > 3) {
			throw new TooManyBikesException(
					"Невозможно доставить " + quantity + " велосипедов модели " + model + " за одну доставку");
		}

	}

	public static void main(String[] args) {

		SellBikes sellBikesEx = new SellBikes();

	}

}
