package com.ta.polibatam.batamgis.model;

public class Industri {
	 	private String nama;
	    private String alamat;
	    private Double lat;
	    private Double lng;
	    private String kategori;
	    private String telp;
	    private String image;


	    public Industri(String nama, String alamat, Double lat, Double lng, String image) {
	        super();
	        this.setNama(nama);
	        this.setAlamat(alamat);
	        this.setLat(lat);
	        this.setLng(lng);
	        this.image = image;
	    }


	    public String getImage() {
	        return image;
	    }
	    public void setImage(String image) {
	        this.image = image;
	    }
	    
		public String getNama() {
			return nama;
		}

		public void setNama(String nama) {
			this.nama = nama;
		}

		public String getAlamat() {
			return alamat;
		}

		public void setAlamat(String alamat) {
			this.alamat = alamat;
		}

		public Double getLat() {
			return lat;
		}

		public void setLat(Double lat) {
			this.lat = lat;
		}

		public Double getLng() {
			return lng;
		}

		public void setLng(Double lng) {
			this.lng = lng;
		}


		public String getKategori() {
			return kategori;
		}


		public void setKategori(String kategori) {
			this.kategori = kategori;
		}


		public String getTelp() {
			return telp;
		}


		public void setTelp(String telp) {
			this.telp = telp;
		}


	}