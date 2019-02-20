package com.webServices.rutas.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class BasicEntity {
	@Getter(value=AccessLevel.PROTECTED)
    @Setter(value=AccessLevel.PROTECTED)
    protected String _class;
}
