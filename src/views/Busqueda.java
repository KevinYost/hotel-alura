package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import jdbc.controller.BusquedaController;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private BusquedaController busquedaController = new BusquedaController();
	private JButton btnEliminar;

	private JTable tbHuespedes;
	private JTable tbReservas;

	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private JTabbedPane tabbedPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public Busqueda() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table,
				null);
		scroll_table.setVisible(true);

		cargarTablaReservas();

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")),
				scroll_tableHuespedes);
		scroll_tableHuespedes.setVisible(true);

		cargarTablaHuespedes();

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscar();
			}
		});
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editar();
			}
		});
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		btnEliminar = new JButton();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eliminar();
//				queFila();
				limpiarTabla();
//				recargarTablaReservas();
//				recargarTablaHuespedes();

			}
		});
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	// Código que permite mover la ventana por la pantalla según la posición de "x"
	// y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}

	protected void editar() {

		String tbReserva = "reservas";
		String tbHuespede = "huespedes";

		if (queFila() == tbReserva) {
			Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
					.ifPresentOrElse(fila -> {
						Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
						Date fechaEntrada = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString());
						Date fechaSalida = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString());
						String valor = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 3);
						String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);

						this.busquedaController.modificarReservas(id, fechaEntrada, fechaSalida, valor, formaPago);
						JOptionPane.showMessageDialog(null, "La reserva de id: " + id + " a sido modificada.");

					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
		} else if (queFila() == tbHuespede) {
			Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
					.ifPresentOrElse(fila -> {
						Integer id = Integer
								.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
						String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
						String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
						Date fechaNacimiento = Date
								.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());
						String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
						Integer telefono = Integer
								.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5).toString());
						Integer idReserva = Integer
								.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());

						this.busquedaController.modificarHuespedes(id, nombre, apellido, fechaNacimiento, nacionalidad,
								telefono, idReserva);
						JOptionPane.showMessageDialog(null, "El huesped de id: " + id + " a sido modificado.");

					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
		} else {
			JOptionPane.showMessageDialog(this, "Por favor, elije un item");
			return;
		}
	}

	protected void buscar() {
		String datos = txtBuscar.getText();
		String where = "where id = " + datos;
		String whereHuesped = "WHERE apellido LIKE '" + datos + "%'";

		if (datos.length() >= 1) {
			try {
				limpiarTb();
				Integer.parseInt(datos);
				cargarTablaHuespedes();

				var reservas = this.busquedaController.buscarReservas(where);
				try {
					reservas.forEach(
							reserva -> modelo.addRow(new Object[] { reserva.get("ID"), reserva.get("FechaEntrada"),
									reserva.get("FechaSalida"), reserva.get("Valor"), reserva.get("FormaPago") }));
				} catch (Exception e) {
					throw e;
				}
			} catch (NumberFormatException e) {
				limpiarTb();
				cargarTablaReservas();
				var huespedes = this.busquedaController.buscarHuespedes(whereHuesped);

				try {
					huespedes.forEach(huesped -> modeloHuesped.addRow(new Object[] { huesped.get("ID"),
							huesped.get("Nombre"), huesped.get("Apellido"), huesped.get("FechaNacimiento"),
							huesped.get("Nacionalidad"), huesped.get("Telefono"), huesped.get("idReserva") }));
				} catch (Exception f) {
					throw f;
				}
			}
		} else {
			limpiarTb();
			cargarTablaHuespedes();
			cargarTablaReservas();
		}

	}

	public void limpiarTb() {
		modelo.getDataVector().clear();
		modeloHuesped.getDataVector().clear();
	}

	private void cargarTablaReservas() {
		var reservas = this.busquedaController.listarReservas();

		try {
			reservas.forEach(reserva -> modelo.addRow(new Object[] { reserva.get("ID"), reserva.get("FechaEntrada"),
					reserva.get("FechaSalida"), reserva.get("Valor"), reserva.get("FormaPago") }));
		} catch (Exception e) {
			throw e;
		}
	}

	private void cargarTablaHuespedes() {
		var reservas = this.busquedaController.listarHuespedes();

		try {
			reservas.forEach(reserva -> modeloHuesped.addRow(new Object[] { reserva.get("ID"), reserva.get("Nombre"),
					reserva.get("Apellido"), reserva.get("FechaNacimiento"), reserva.get("Nacionalidad"),
					reserva.get("Telefono"), reserva.get("idReserva") }));
		} catch (Exception e) {
			throw e;
		}
	}

	private Object queFila() {
		if ((tbHuespedes.getSelectedRowCount() == 0 || tbHuespedes.getSelectedColumnCount() == 0)
				&& (tbReservas.getSelectedRowCount() == 0 || tbReservas.getSelectedColumnCount() == 0)) {
			JOptionPane.showInternalMessageDialog(null, "Por favor seleccione un item a eliminar.");
		} else if ((tbHuespedes.getSelectedRowCount() == 0 || tbHuespedes.getSelectedColumnCount() == 0)
				&& (tbReservas.getSelectedRowCount() != 0 || tbReservas.getSelectedColumnCount() != 0)) {
			String R = "reservas";
			return R;
		} else {
			String H = "huespedes";
			return H;
		}
		return null;
	}

	private void limpiarTabla() {
		Busqueda busqueda = new Busqueda();
		busqueda.setVisible(true);
		dispose(); // Tenia un pequeno error a la hora de intentar que se recargue la pagina,
					// siempre quedaba seleccionada
					// la tabla de huespedes, por ellos preferi recargar la pantalla cada vez que se
					// elimine un item de cualquiera de ellas
	}

	private void eliminar() {
		String tbReserva = "reservas";
		String tbHuespede = "huespedes";

		if (queFila() == tbReserva) {
			Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
					.ifPresentOrElse(fila -> {
						Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());

						System.out.print(id);
						int cantidadEliminada;

						cantidadEliminada = this.busquedaController.eliminarReservas(id);

						modelo.removeRow(tbReservas.getSelectedRow());

						JOptionPane.showMessageDialog(this, "Reserva numero: " + id + " se a eliminado con exito.");
					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elija una reserva."));

		} else if (queFila() == tbHuespede) {
			Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
					.ifPresentOrElse(fila -> {

						Integer id = Integer
								.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());

						System.out.print(id);

						int cantidadEliminada;

						cantidadEliminada = this.busquedaController.eliminarHuesped(id);

						modeloHuesped.removeRow(tbHuespedes.getSelectedRow());

						JOptionPane.showMessageDialog(this, "Huesped numero: " + id + " a sido eliminado con exito.");
					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elija un huesped."));
		}
	}
}
