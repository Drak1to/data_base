package two.models;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Movie {
    private int id;
    private String descriptions;
    private int id_rating;
    private String name_movie;



}
