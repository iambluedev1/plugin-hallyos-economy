
CREATE TABLE `hallyos_i18n` (
  `message_id` int(7) NOT NULL,
  `lang_id` int(7) NOT NULL,
  `message_key` text NOT NULL,
  `message_value` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `hallyos_i18n` (`message_id`, `lang_id`, `message_key`, `message_value`) VALUES
(1, 0, 'i18n_reload_success', '&2Le cache des messages bien été réinitialisé. '),
(2, 0, 'i18n_cant_reload', '&4Vous n\'avez pas la permission d\'effectuer cette commande !'),
(3, 0, 'global_not_allowed', '&4Vous n\'avez pas la permission d\'effectuer cette commande !'),
(4, 0, 'economy_help_give', '&6/hgive <nom du joueur> <montant>'),
(5, 0, 'economy_error_amount', '&cMerci de vérifier le montant que vous avez indiqué.'),
(6, 0, 'economy_receive_success', '&aVotre compte vient d\'être crédité de &b{0}&a rubis !'),
(7, 0, 'economy_send_success', '&aVous venez de créditer le compte de &b{1} &ade &b{0}&a rubis !'),
(8, 0, 'economy_error_hasnt_money', '&cVous n\'avez pas assez de rubis sur votre compte pour effectuer cette transaction.'),
(9, 0, 'economy_receive_from_success', '&b{1} vient de vous envoyer &b{0} &arubis !'),
(10, 0, 'economy_send_to_success', '&aVous venez d\'envoyer &b{0} &arubis à &b{1} &a.'),
(11, 0, 'economy_help_pay', '&6/hpay <nom du joueur> <montant>'),
(12, 0, 'economy_view_balance', '&aVous avez &b{0} &arubis sur votre compte.'),
(13, 0, 'economy_help_money_1', '&6/hmoney'),
(14, 0, 'economy_help_money_2', '&6/hmoney <nom du joueur>'),
(15, 0, 'economy_view_balance_of', '&b{1} &aa &b{0} &arubis sur son compte.'),
(16, 0, 'economy_help_remove', '&6/hremove <nom du joueur> <montant>'),
(17, 0, 'economy_receive_remove_success', '&aUne somme de &b{0} &arubis vient d\'être retirée de votre compte.'),
(18, 0, 'economy_send_remove_success', '&aVous venez de retirer une somme de &b{0} &arubis a &b{1} !'),
(22, 0, 'economy_alert', '&c[ALERTE] TRANSACTION ELEVEE ENTRE &9{1} &cET &9{2} &cPOUR UN MONTANT DE &9{0} &crubis.'),
(23, 0, 'economy_error_pay_yourself', '&cVous ne pouvez pas vous payez vous même :) !'),
(24, 0, 'global_no_enough_money', '&cVous n\'avez pas assez d\'argent sur votre compte.');

CREATE TABLE `hallyos_transactions` (
  `transaction_id` int(7) NOT NULL,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `transaction_amount` int(11) NOT NULL DEFAULT 0,
  `transaction_type` varchar(15) NOT NULL,
  `created_at` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE `hallyos_users` (
  `user_id` int(7) NOT NULL,
  `user_login` varchar(16) NOT NULL,
  `user_money` int(11) NOT NULL DEFAULT 0,
  `created_at` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

ALTER TABLE `hallyos_i18n`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `message_id` (`message_id`);

ALTER TABLE `hallyos_transactions`
  ADD PRIMARY KEY (`transaction_id`);

ALTER TABLE `hallyos_users`
  ADD PRIMARY KEY (`user_id`);

ALTER TABLE `hallyos_i18n`
  MODIFY `message_id` int(7) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

ALTER TABLE `hallyos_transactions`
  MODIFY `transaction_id` int(7) NOT NULL AUTO_INCREMENT;

ALTER TABLE `hallyos_users`
  MODIFY `user_id` int(7) NOT NULL AUTO_INCREMENT;
