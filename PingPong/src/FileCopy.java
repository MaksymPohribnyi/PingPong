import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

public class FileCopy implements ActionListener {

	FileInputStream mainFileReader;
	BufferedInputStream buffReader;
	FileOutputStream copyFileWriter;
	BufferedOutputStream buffWriter;

	JLabel labelCopyFrom;
	JLabel labelCopyTo;
	JTextField fieldCopyFrom;
	JTextField fieldCopyTo;
	JButton browseCopyPath;
	JButton browseInsertPath;
	JButton buttonCopy;

	String mainFile = "";
	String dirCopyPath = "";

	JFileChooser fileCh = new JFileChooser();

	public FileCopy() {

		labelCopyFrom = new JLabel("Copy from:");
		fieldCopyFrom = new JTextField();

		labelCopyTo = new JLabel("Copy to:");
		fieldCopyTo = new JTextField();

		browseCopyPath = new JButton("...");
		browseCopyPath.addActionListener(this);

		browseInsertPath = new JButton("...");
		browseInsertPath.addActionListener(this);

		buttonCopy = new JButton("Copy");
		buttonCopy.addActionListener(this);

		JPanel centerPanel = new JPanel();
		GridLayout gl = new GridLayout(2, 3);
		centerPanel.setLayout(gl);

		centerPanel.add(labelCopyFrom);
		centerPanel.add(fieldCopyFrom);
		centerPanel.add(browseCopyPath);

		centerPanel.add(labelCopyTo);
		centerPanel.add(fieldCopyTo);
		centerPanel.add(browseInsertPath);

		JPanel windowContent = new JPanel();
		BorderLayout bl = new BorderLayout();
		windowContent.setLayout(bl);

		windowContent.add("Center", centerPanel);
		windowContent.add("South", buttonCopy);

		JFrame myFrame = new JFrame("File copy");
		myFrame.setContentPane(windowContent);
		myFrame.setSize(600, 100);
		myFrame.setVisible(true);

	}

	public void actionPerformed(ActionEvent event) {

		JButton src = (JButton) event.getSource();

		if (src == browseCopyPath) {

			int ret = fileCh.showDialog(null, "Choose a file to copy:");

			if (ret == JFileChooser.APPROVE_OPTION) {
				File fileDir = fileCh.getSelectedFile();
				mainFile = fileDir.getPath();
				fieldCopyFrom.setText(mainFile);
			}

		} else if (src == browseInsertPath) {

			fileCh.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int ret = fileCh.showDialog(null, "Choose a dir to insert:");

			if (ret == JFileChooser.APPROVE_OPTION) {
				File fileDir = fileCh.getSelectedFile();
				dirCopyPath = fileDir.getPath();
				fieldCopyTo.setText(dirCopyPath);
			}
		} else {

			mainFile = fieldCopyFrom.getText();
			dirCopyPath = fieldCopyTo.getText();

			// Значит нажали кнопку "Копировать"
			if (mainFile.equals("") || dirCopyPath.equals("")) {
				JOptionPane.showMessageDialog(null, "Before copy enter a copy from and copy to path`s!",
						"Fill in both path`s", JOptionPane.PLAIN_MESSAGE);
				return;
			}

			// Дальше блок проверки существования директории главного файла
			// и создание буфера для чтения главного файла
			try {

				mainFileReader = new FileInputStream(mainFile);
				buffReader = new BufferedInputStream(mainFileReader);

				dirCopyPath += "\\Backup";
				copyFileWriter = new FileOutputStream(dirCopyPath);
				buffWriter = new BufferedOutputStream(copyFileWriter);

				while (true) {
					int valueOfBytes = buffReader.read();
					if (valueOfBytes == -1) {
						break;
					}
					buffWriter.write(valueOfBytes);
				}

				JOptionPane.showMessageDialog(null, "File copied!", "It`s OK", JOptionPane.PLAIN_MESSAGE);

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					mainFileReader.close();

				} catch (IOException e) {

					e.printStackTrace();

				} finally {

					try {

						buffReader.close();

					} catch (IOException e2) {

						e2.printStackTrace();

					} finally {

						try {

							copyFileWriter.close();

						} catch (IOException e) {

							e.printStackTrace();

						} finally {

							try {

								buffWriter.flush();
								buffWriter.close();

							} catch (IOException e3) {

								e3.printStackTrace();

							}
						}

					}
				}
			}

		}

	}

	public static void main(String[] args) {

		FileCopy myFileCopy = new FileCopy();

	}

}
