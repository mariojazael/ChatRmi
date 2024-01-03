package View;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class SerializableGroupLayout extends GroupLayout implements Serializable {

    public SerializableGroupLayout(Container host) {
        super(host);
    }

}
