package com.leandoer.entity.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leandoer.entity.Manufacturer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"links"}, ignoreUnknown = true)
public class ManufacturerModel extends RepresentationModel<ManufacturerModel> {
    private Long id;
    private String name;

    public ManufacturerModel(Manufacturer manufacturer) {
        this.id = manufacturer.getId();
        this.name = manufacturer.getName();
    }

    public Manufacturer toManufacturer() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(this.id);
        manufacturer.setName(this.name);
        return manufacturer;
    }
}
