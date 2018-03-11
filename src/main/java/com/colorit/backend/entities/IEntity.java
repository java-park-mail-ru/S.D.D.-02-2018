package com.colorit.backend.entities;

import com.colorit.backend.views.output.AbstractOutputView;

public interface IEntity {
    AbstractOutputView toView();
}
