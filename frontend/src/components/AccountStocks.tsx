import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Grid,
  Typography,
} from "@mui/material";

import { useAccount, useStockAccountFind } from "../hooks/queries";

type Stock = {
  id?: number;
  userId?: number;
  currency?: string;
  amount?: number;
  default?: boolean;
};

const Stock: React.FC<{ stock: Stock }> = (
  {
    // stock: {  name, sign, description, currentStockPrice },
  }
) => {
  return (
    <Card sx={{ m: 1 }}>
      <CardContent>
        <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          {/*{name} ${currentStockPrice}*/}
        </Typography>
        <Typography variant="h5" component="div">
          {/*{sign}*/}
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">информация</Button>
      </CardActions>
    </Card>
  );
};

const AccountStocks: React.FC<{ filter?: string }> = ({ filter = "" }) => {
  // const { data = [] } = useStockAccountFind();
  //
  // if (!data.length)
  //   return (
  //     <Box
  //       sx={{
  //         display: "flex",
  //         alignItems: "center",
  //         justifyContent: "center",
  //         width: "100%",
  //       }}
  //     >
  //       <Typography variant="h5" component="div">
  //         No stocks
  //       </Typography>
  //     </Box>
  //   );
  // return (
  //   <Box sx={{ width: "100%" }}>
  //     <Typography sx={{ m: 1, ml: 4 }}>Stocks</Typography>
  //     <Grid container>
  //       {data
  //         .filter(
  //           ({ name = "", sign = "" }) =>
  //             name.includes(filter) || sign.includes(filter)
  //         )
  //         .map((stock) => (
  //           <Grid key={stock.id} item xs={3}>
  //             {/*<Stock stock={stock} />*/}
  //           </Grid>
  //         ))}
  //     </Grid>
  //   </Box>
  // );

  return null;
};

export default AccountStocks;
