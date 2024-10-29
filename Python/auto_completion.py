# Script de démonstration pour l'autocomplétion

def calculer_statistiques(liste_valeurs):
    """
    Calcule les statistiques de base pour une liste de valeurs.
    
    :param liste_valeurs: Liste de nombres
    :return: Un dictionnaire contenant la moyenne, la médiane et l'écart-type des valeurs
    """
    # Calcul de la moyenne
    moyenne = sum(liste_valeurs) / len(liste_valeurs)
    
    # Calcul de la médiane
    liste_triee = sorted(liste_valeurs)
    n = len(liste_valeurs)
    if n % 2 == 0:
        mediane = (liste_triee[n // 2 - 1] + liste_triee[n // 2]) / 2
    else:
        mediane = liste_triee[n // 2]

    # Calcul de l'écart-type (à compléter)
    somme_carre_diff = # Complétez ici
    ecart_type =  # Complétez ici

    
    return {
        "moyenne": moyenne,
        "mediane": mediane,
        "ecart_type": ecart_type
    }

# Exemple d'utilisation
valeurs = [12, 15, 23, 26, 29, 35, 40]
statistiques = calculer_statistiques(valeurs)
print("Statistiques :", statistiques)

