package uz.pdp.appclickup.entity.template;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbsLONGEntity extends AbsMAINEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
