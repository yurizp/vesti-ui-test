package mobi.vesti;

import com.steadystate.css.parser.Locatable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

public interface Element extends WebElement, WrapsElement, Locatable {}