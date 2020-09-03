package mobi.vesti;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class Mascara {
  public static String telefone(String telefone) throws ParseException {
    MaskFormatter maskFormatter = new MaskFormatter("## ########");
    maskFormatter.setValueContainsLiteralCharacters(false);
    return maskFormatter.valueToString(telefone);
  }

  public static String cnpj(String cnpj) throws ParseException {
    MaskFormatter maskFormatter = new MaskFormatter("##.###.###/####-##");
    maskFormatter.setValueContainsLiteralCharacters(false);
    return maskFormatter.valueToString(cnpj);
  }
}
