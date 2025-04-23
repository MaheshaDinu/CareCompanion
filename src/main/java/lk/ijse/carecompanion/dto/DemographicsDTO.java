package lk.ijse.carecompanion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DemographicsDTO {
    private String title;
    private List<String> categories;
    private List<Integer> counts;
}
