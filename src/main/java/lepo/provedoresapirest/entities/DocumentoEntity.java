package lepo.provedoresapirest.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "documento_comercial", schema = "mergos")
public class DocumentoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tipo_documento_id")
	private TipoDocumentoEntity tipoDocumento;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "proveedor_id")
	private ProveedorEntity proveedor;

	@Column(name = "monto")
	private Double monto;

	@Column(name = "nroFactura")
	private String nroFactura;

	@Column(name = "fecha_factura")
	private Date fechaFactura;

	@Column(name = "fecha_pagada")
	private Date fechaPagada;

	@Column(name = "esta_pagada")
	private Boolean estaPagada;

	@Column(name = "fecha_insert")
	private Date fechaInsert;

	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;

	@Column(name = "fecha_delete")
	private Date fechaDelete;

	public DocumentoEntity() {
	}

	public DocumentoEntity(TipoDocumentoEntity tipoDocumento, ProveedorEntity proveedor, Double monto,
			String nroFactura, Date fechaFactura, Date fechaInsert) {
		super();
		this.tipoDocumento = tipoDocumento;
		this.proveedor = proveedor;
		this.monto = monto;
		this.nroFactura = nroFactura;
		this.fechaFactura = fechaFactura;
		this.fechaInsert = fechaInsert;
		this.estaPagada = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoDocumentoEntity getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoEntity tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public ProveedorEntity getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedorEntity proveedor) {
		this.proveedor = proveedor;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Date getFechaPagada() {
		return fechaPagada;
	}

	public void setFechaPagada(Date fechaPagada) {
		this.fechaPagada = fechaPagada;
	}

	public Boolean getEstaPagada() {
		return estaPagada;
	}

	public void setEstaPagada(Boolean estaPagada) {
		this.estaPagada = estaPagada;
	}

	public Date getFechaInsert() {
		return fechaInsert;
	}

	public void setFechaInsert(Date fechaInsert) {
		this.fechaInsert = fechaInsert;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaDelete() {
		return fechaDelete;
	}

	public void setFechaDelete(Date fechaDelete) {
		this.fechaDelete = fechaDelete;
	}

}
