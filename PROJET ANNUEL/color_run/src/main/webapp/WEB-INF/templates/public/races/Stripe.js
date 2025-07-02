const stripe = Stripe('pk_test_XXXXXX'); // Clé publique de test

document.getElementById("nextButton").addEventListener("click", async () => {
// Récupérer le prix (par ex. 25€ => 2500 centimes)
const amount = 2500;

// Créer PaymentIntent côté serveur
const res = await fetch('/create-payment-intent', {
method: 'POST',
headers: {'Content-Type': 'application/x-www-form-urlencoded'},
body: `amount=${amount}`
});

    const {clientSecret} = await res.json();

    // Monter le CardElement
    const elements = stripe.elements();
    const card = elements.create('card');
    card.mount('#card-element');

    // Afficher le formulaire de paiement
    document.getElementById('paymentForm').classList.remove('hidden');
    document.getElementById('registrationForm').scrollIntoView({ behavior: 'smooth' });

    // Soumettre la carte au clic "Payer"
    document.getElementById("nextButton").addEventListener("click", async () => {
    const {paymentIntent, error} = await stripe.confirmCardPayment(clientSecret, {
    payment_method: {
    card: card,
}
});

    if (error) {
    document.getElementById('card-errors').textContent = error.message;
} else {
    if (paymentIntent.status === 'succeeded') {
    alert('Paiement réussi !');
    // Optionnel : POST vers ton backend pour enregistrer l'inscription
}
}
});
});
