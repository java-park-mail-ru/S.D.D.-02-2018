package com.colorit.backend.entities;

import com.colorit.backend.views.output.IOutputView;

public interface IEntity {
    IOutputView toView();
}
