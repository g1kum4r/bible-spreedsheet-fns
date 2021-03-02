package com.spreedsheet.data;

/**
 * @author jeeva
 *
 */
public class EvaluteRow {
	protected String genesis;
	protected int vowels;
	protected int constants;
	protected int letters;

	public EvaluteRow(String genesis, int vowels, int constants, int letters) {
		super();
		this.genesis = genesis;
		this.vowels = vowels;
		this.constants = constants;
		this.letters = letters;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * vowels + constants;  
        return result;
    }

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof EvaluteRow) {
			EvaluteRow ev = (EvaluteRow) obj;
			return ev.constants == this.constants && ev.vowels == this.vowels;
		}

		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "EvaluteRow [genesis=" + genesis + ", vowels=" + vowels + ", constants=" + constants + ", letters="
				+ letters + "]";
	}

}
