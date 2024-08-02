INSERT INTO balances (category, amount) VALUES ('FOOD', 1000.0);
INSERT INTO balances (category, amount) VALUES ('MEAL', 1000);
INSERT INTO balances (category, amount) VALUES ('CASH', 1000);

INSERT INTO mcc_category (start_mcc, end_mcc, category) VALUES (5400, 5499, 'FOOD');
INSERT INTO mcc_category (start_mcc, end_mcc, category) VALUES (5800, 5899, 'MEAL');

INSERT INTO merchants (merchant_name, mcc) VALUES ('UBER TRIP', 5911);
INSERT INTO merchants (merchant_name, mcc) VALUES ('UBER EATS', 5811);
INSERT INTO merchants (merchant_name, mcc) VALUES ('PAG*JoseDaSilva', 5211);
INSERT INTO merchants (merchant_name, mcc) VALUES ('PICPAY*BILHETEUNICO', 7811);