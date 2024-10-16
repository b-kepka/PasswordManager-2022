import javax.swing.*;

public class Menu extends JMenuBar {
    JMenu bazyMenu, info;
    JMenuItem test,nowyWpis,usunWpis,zamknij,help,szukaj,szukajLogin,szukajAdres;
    public Menu(){
        bazyMenu = new JMenu("Bazy");
        test = new JMenuItem("Test połączenia");
        nowyWpis = new JMenuItem("Nowy wpis");
        usunWpis = new JMenuItem("Usuń wpis");
        zamknij = new JMenuItem("Zamknij");
        help = new JMenuItem("Help");
        szukaj = new JMenu("Szukaj");
        info = new JMenu("Info");
        szukajLogin = new JMenuItem("w/g loginu");
        szukajAdres = new JMenuItem("w/g adresu");
        bazyMenu.add(test);
        bazyMenu.add(nowyWpis);
        bazyMenu.add(usunWpis);
        bazyMenu.add(zamknij);
        szukaj.add(szukajLogin);
        szukaj.add(szukajAdres);
        info.add(help);
        this.add(bazyMenu);
        this.add(szukaj);
        this.add(info);
    }
}
