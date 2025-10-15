CREATE TABLE transactions(
    transaction_id UUID NOT NULL,
    sender_id UUID,
    title VARCHAR(255),
    value DOUBLE PRECISION,
    type_transaction VARCHAR(255),
    method_payment VARCHAR(255),
    date_of_payment DATE,
    PRIMARY KEY(transaction_id),

    CONSTRAINT fk_transactions_sender_id
    FOREIGN KEY( sender_id)
    REFERENCES users(user_id)
);