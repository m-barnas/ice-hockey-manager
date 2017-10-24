package cz.muni.fi.pa165.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HockeyPlayer {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
