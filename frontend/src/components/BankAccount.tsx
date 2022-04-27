import { useState } from "react";
import {
  Box,
  Button,
  Chip,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Modal,
  Rating,
  Select,
  TextField,
  Typography,
} from "@mui/material";

import { useUserAccount, useUserId } from "../lib";
import {
  useBank,
  useBankExchange,
  useBankExchangeFind,
  useBankReviewFind,
  useUserExchangeCurrency,
} from "../queries";

const BankAccount: React.FC = () => {
  const [fromCurrency, setFromCurrency] = useState("");
  const [toCurrency, setToCurrency] = useState("");
  const [exchangeAmount, setExchangeAmount] = useState(0);
  const [exchangeBank, setExchangeBank] = useState(0);

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    // setToCurrency("");
    // setFromCurrency("");
    setExchangeAmount(0);
    setExchangeBank(0);
  };

  const [{ data: bankAccount = [] }, usdAmount] = useUserAccount();
  const [userId] = useUserId();
  const { mutate: exchange } = useUserExchangeCurrency();

  const { data: banks = [] } = useBank();
  const { data: banksRates = [] } = useBankExchange();

  const { data: bankReviews = [] } = useBankReviewFind({
    bankId: exchangeBank,
  });

  return (
    <>
      <Chip label={`$${usdAmount}`} onClick={handleOpen} />
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <>
          {!!exchangeBank && !!bankReviews.length && (
            <Box sx={styleReviews}>
              <Typography variant="h5" sx={{ textAlign: "center", mb: 1 }}>
                Reviews
              </Typography>
              <Box sx={{ maxHeight: 300, overflow: "auto", p: 1, pr: 2 }}>
                {bankReviews.map((review) => (
                  <Box
                    key={review.id}
                    sx={{
                      mt: 1,
                      p: 1,
                      borderRadius: 1,
                      backgroundColor: "rgba(0,0,0,0.02)",
                    }}
                  >
                    <Rating value={review.rate} readOnly />
                    <Typography>{review.review}</Typography>
                    {!!review.createdAt && (
                      <Typography
                        sx={{ fontSize: 10, mt: 0.5 }}
                        color="text.secondary"
                      >
                        {new Date(review.createdAt).getDate()}.
                        {new Date(review.createdAt).getMonth()}.
                        {new Date(review.createdAt).getFullYear()}
                      </Typography>
                    )}
                  </Box>
                ))}
              </Box>
            </Box>
          )}
          <Box sx={style}>
            <Typography variant="h5" sx={{ textAlign: "center" }}>
              Accounts
            </Typography>

            {bankAccount.map((account) => (
              <Typography
                key={account.id}
                sx={{ fontSize: 14 }}
                color="text.secondary"
                gutterBottom
              >
                {account.currency}: {account.amount}
              </Typography>
            ))}
            <Box sx={{ mt: 2 }}>
              <FormControl sx={{ mt: 1, mb: 1 }} fullWidth>
                <InputLabel id="demo-simple-select-label">Bank</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  label="Bank"
                  value={exchangeBank}
                  onChange={(e) => setExchangeBank(+e.target.value)}
                >
                  {banks.map(({ id, name, description, country }) => (
                    <MenuItem key={id} value={id}>
                      <Box>
                        <Typography>
                          {name} ({country})
                        </Typography>
                        <Typography
                          sx={{ fontSize: 12 }}
                          color="text.secondary"
                        >
                          {description}
                        </Typography>

                        {banksRates
                          .slice()
                          .filter(({ bankId }) => bankId === id)
                          .map((rate) => (
                            <Typography
                              key={rate.id}
                              sx={{ fontSize: 14 }}
                              color="text.secondary"
                            >
                              {rate.currencyIn}/{rate.currencyOut} rate:{" "}
                              {rate.rate} fee: {rate.fee}
                            </Typography>
                          ))}
                      </Box>
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Box>
            <Box sx={{ display: "flex", mt: 1, mb: 2 }}>
              <FormControl sx={{ mr: 2 }} fullWidth>
                <InputLabel id="demo-simple-select-label">From</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  label="From"
                  value={fromCurrency}
                  onChange={(e) => setFromCurrency(e.target.value)}
                >
                  {bankAccount.map(({ currency }) => (
                    <MenuItem key={currency} value={currency}>
                      {currency}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
              <FormControl sx={{ mr: 2 }} fullWidth>
                <InputLabel id="demo-simple-select-label">To</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  label="To"
                  value={toCurrency}
                  onChange={(e) => setToCurrency(e.target.value)}
                  disabled={!fromCurrency}
                >
                  {bankAccount
                    .slice()
                    .filter(({ currency }) => currency !== fromCurrency)
                    .map(({ currency }) => (
                      <MenuItem key={currency} value={currency}>
                        {currency}
                      </MenuItem>
                    ))}
                </Select>
              </FormControl>
              <TextField
                type="number"
                fullWidth
                value={exchangeAmount}
                onChange={(e) => setExchangeAmount(Math.abs(+e.target.value))}
                disabled={!fromCurrency || !toCurrency}
              />
            </Box>
            <Box sx={{ m: 1 }}>
              <Button
                fullWidth
                disabled={!fromCurrency || !toCurrency || !exchangeAmount}
                onClick={() =>
                  exchange({
                    userId: userId as number,
                    bankId: +exchangeBank,
                    currencyIn: fromCurrency,
                    currencyOut: toCurrency,
                    amount: exchangeAmount,
                  })
                }
              >
                Exchange
              </Button>
            </Box>
          </Box>
        </>
      </Modal>
    </>
  );
};

export default BankAccount;

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  borderRadius: 4,
  boxShadow: 24,
  p: 4,
};

const styleReviews = {
  position: "absolute" as "absolute",
  top: "50%",
  right: "50%",
  transform: "translate(200%, -50%)",
  width: 200,
  bgcolor: "background.paper",
  borderRadius: 4,
  boxShadow: 24,
  p: 4,
};
