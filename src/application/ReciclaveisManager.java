package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReciclaveisManager {
	private HashMap<String, Double> reciclaveis = new HashMap<>();
	private Double pontos = 0.0;
	private int meta = 100;

	private static final String DATA_FILE = "regiclagem_dados.txt";

	public void registrar(String material, Double quantidade) {
		reciclaveis.put(material, reciclaveis.getOrDefault(material, (double) 0) + quantidade);
		pontos += quantidade;
		pontos = Math.round(pontos * 100.0) / 100.0;
		salvarDados();
	}


	public String status() {
		StringBuilder status = new StringBuilder("");
		for (Map.Entry<String, Double> entry : reciclaveis.entrySet()) {
			status.append(entry.getKey()).append(": ").append(entry.getValue()).append(" kg").append("\n");
		}
		return status.toString();
	}



	public void salvarDados() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
			
			writer.write("Meta: " + meta);
			writer.newLine();
			
			for (Map.Entry<String, Double> entry : reciclaveis.entrySet()) {
				writer.write(entry.getKey() + ": " + entry.getValue());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregarDados() {
		try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
			String line;
			boolean isMetaLine = true;
			
			while ((line = reader.readLine()) != null) {
				
				if(isMetaLine) {
					String[] parts = line.split(":");
					this.meta = Integer.valueOf(parts[1].trim());
					isMetaLine = false;
				}else {
					String[] parts = line.split(":");
					if (parts.length == 2) {
						String material = parts[0].trim();
						Double quantidade = Double.valueOf(parts[1].trim());
						reciclaveis.put(material, quantidade);
						pontos += quantidade;
					}
				}	

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limparDados() {
		reciclaveis.clear();
		pontos = 0.0;
		salvarDados();
	}
	
	public Double getPontos() {
		return this.pontos;
	}
	
	public int getMeta() {
		return this.meta;
	}
	
	public void setMeta(int meta) {
		this.meta = meta;
		salvarDados();
	}
}
