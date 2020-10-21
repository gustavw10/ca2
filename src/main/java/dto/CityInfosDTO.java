package dto;

import entities.CityInfo;
import java.util.ArrayList;
import java.util.List;

public class CityInfosDTO {

    List<CityInfoDTO> all = new ArrayList();

    public CityInfosDTO(List<CityInfo> cityInfoEntities) {
        cityInfoEntities.forEach((c) -> {
            all.add(new CityInfoDTO(c));
        });
    }

    public CityInfosDTO() {
    }

    public List<CityInfoDTO> getAll() {
        return all;
    }

    public void setAll(List<CityInfoDTO> all) {
        this.all = all;
    }
}
