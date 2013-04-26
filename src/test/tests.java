package test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import domeinLaag.*;

public class tests {

	protected static LuchtvaartMaatschappij lvm;
	protected static Fabrikant f1, f2;
	protected static VliegtuigType vtt1, vtt2, vtt3, vtt4;
	protected static Vliegtuig vt1, vt2, vt3;
	protected static Land l1, l2, l3, l4; 
	protected static Luchthaven lh1, lh2, lh3;
	protected static Vlucht v1;
	protected static Calendar vertrek, aankomst;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lvm = new LuchtvaartMaatschappij("NLM");
		f1 = new Fabrikant("Airbus","G. Dejenelle");
		f2 = new Fabrikant("Fokker","A.J. Berger");

		vtt1 = f1.creeerVliegtuigType("A-200", 60, 80);
		vtt2 = f1.creeerVliegtuigType("A-201", 65, 88);
		vtt3 = f2.creeerVliegtuigType("F-100", 37, 70);
		vtt4 = f2.creeerVliegtuigType("F-101", 45, 82);

		Calendar datum = Calendar.getInstance();
		datum.set(2000, 01, 01);
		vt1 = new Vliegtuig(lvm, vtt1, "Luchtbus 100", datum);
		datum.set(2006, 06, 01);
		vt2 = new Vliegtuig(lvm, vtt3, "Karel Doorman", datum);
		datum.set(1999, 11, 01);
		vt3 = new Vliegtuig(lvm, vtt4, "Fokke", datum);

		l1 = new Land("Nederland", 31);
		l2 = new Land("Belgie", 32);
		l3 = new Land("Frankrijk", 11);
		l4 = new Land("Duitsland", 13);

		lh1 = new Luchthaven("Schiphol", "ASD", true, l1);
		lh2 = new Luchthaven("Charles de Gaule", "PRI", true, l3);
		lh3 = new Luchthaven("Tegel", "TEG", true, l4);

		vertrek = Calendar.getInstance();
		vertrek.set(2008, 0, 1, 12, 0);
		aankomst = Calendar.getInstance();
		aankomst.set(2008, 0, 2, 12, 0);
		v1 = new Vlucht(vt3, lh1, lh2, vertrek, aankomst);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test (expected=VluchtException.class)
	public void test1() throws VluchtException {
		/** 
		 * 	Test 1
		 * 	Vlucht: Fokke van Schiphol naar Schiphol
		 * 	vertrektijd: null , aankomsttijd null
		 * 	Foutmelding: Vertrekpunt = Bestemming
		 * 
		 * Dimtier: even, Robin: oneven
		**/
		
		vertrek.set(2008, 0, 1, 12, 0);
		aankomst.set(2008, 0, 1, 12, 30);

		Vlucht testVlucht = new Vlucht(vt3, lh1, lh1, vertrek, aankomst);
		
	}

}
