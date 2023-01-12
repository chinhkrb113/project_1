package hust.pms.controller;

import hust.common.Constants;
import hust.pms.model.Card;

public class CardController {
	public void addCard(Card pc) {
		pc.addCard();
	}
	
	public boolean isCardExist(String cardID) {
		Card c = new Card();
		return c.isCardExist(cardID);
	}
	
	public boolean isCardBeUsed(String cardID) {
		Card c = new Card();
		//c.isCardBeUsed(cardID);
		if (c.getCardStatus(cardID) == Constants.CARD_LOCKED || c.getCardStatus(cardID) == Constants.CARD_NOT_ACTIVATED) {
			return false;
		} else if (c.getCardStatus(cardID) == Constants.CARD_NOT_EXIST) {
			return false;
		} else if (c.getCardStatus(cardID) == Constants.CARD_NORMAL) {
			return true;
		}
		return false;
	}
	
	public byte getCardType(String cardID) {
		Card c = new Card();
		return c.getCardType(cardID);
	}
	
	public void promoteToCard(String cardID) {
		Card c = new Card();
		c.promoteToCard(Constants.CARD_FOR_CUSTOMER, cardID);
	}
}
